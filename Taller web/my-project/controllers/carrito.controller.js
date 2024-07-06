const Product = require('../models/Product.js');
const Order = require('../models/Order.js');


// Método para obtener productos por id_commerce
exports.getProductsByUsers = async (req, res) => {
    try {
        const cart = req.session.cart || []; 

        if (cart.length === 0) {
            return res.render('carrito', { products: [], user: req.session.user });
        }

        const cartProductIds = cart.map(item => item.productId);
        const dbProducts = await Product.find({ _id: { $in: cartProductIds } });

        // Filtrar y agregar cantidad desde el carrito
        const cartProducts = dbProducts.map(product => {
            const cartItem = cart.find(item => item.productId === product._id.toString());
            return {
                ...product._doc,
                quantity: cartItem ? cartItem.quantity : 0
            };
        });

        res.render('carrito', { products: cartProducts, user: req.session.user });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
};


exports.addProduct = async (req, res) => {
    const productId = req.params.productId;

    try {
        if (!req.session.cart) {
            req.session.cart = [];
        }

        const product = await Product.findById(productId);
        if (!product) {
            return res.status(404).json({ message: 'Producto no encontrado' });
        }

        const cartProductIndex = req.session.cart.findIndex(item => item.productId === productId);
        if (cartProductIndex !== -1) {
            req.session.cart[cartProductIndex].quantity += 1;
        } else {
            req.session.cart.push({ productId: productId, quantity: 1 });
        }

        res.redirect('/products');
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error del servidor' });
    }
};



exports.confirmCart = async (req, res) => {
    try {
        const cart = req.session.cart;
        const user = req.session.user;

        if (!cart || cart.length === 0) {
            return res.status(400).json({ message: 'El carrito está vacío' });
        }

        const productIds = cart.map(item => item.productId);
        const products = await Product.find({ _id: { $in: productIds } });

        for (let i = 0; i < cart.length; i++) {
            const cartItem = cart[i];
            const product = products.find(p => p._id.equals(cartItem.productId));

            if (!product) {
                throw new Error('Producto no encontrado en la base de datos');
            }

            if (product.stock < cartItem.quantity) {
                throw new Error(`No hay suficiente stock para ${product.name}`);
            }
        }

        
        // Crear nuevas órdenes y actualizar el stock de cada producto en el carrito
        const orders = await Promise.all(cart.map(async item => {
            const product = await Product.findById(item.productId);

            if (!product) {
                throw new Error('Producto no encontrado');
            }

            const newOrder = new Order({
                user: user.id,
                commerce: product.commerce,
                product: item.productId,
                quantity: item.quantity,
                totalAmount: item.quantity * product.price
            });

            await newOrder.save();

            // Actualizar el stock del producto después de confirmar la compra
            product.stock -= item.quantity;
            await product.save();
            if (product.stock == 0){
                await Product.deleteOne({ _id: product });
            }

            return newOrder;
        }));
        

        // Vaciar el carrito después de confirmar la compra
        req.session.cart = [];

        res.redirect('/products');
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error al confirmar el carrito' });
    }
};




// Cancelar carrito
exports.cancelCart = async (req, res) => {
    try {
        if (!req.session.cart) {
            return res.redirect('/products');
        }

        req.session.cart = [];
        res.redirect('/products');
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error al cancelar el carrito' });
    }
};



// Eliminar producto del carrito
exports.removeProduct = async (req, res) => {
    const productId = req.params.productId;

    try {
        const cartItem = req.session.cart.find(item => item.productId === productId);
        if (!cartItem) {
            return res.status(404).json({ message: 'Producto no encontrado en el carrito' });
        }

        const product = await Product.findById(productId);
        if (!product) {
            return res.status(404).json({ message: 'Producto no encontrado' });
        }

        product.stock += cartItem.quantity;
        await product.save();
        req.session.cart = req.session.cart.filter(item => item.productId !== productId);
        res.redirect('/carrito'); // Redirigir a la página del carrito u otra página adecuada
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error al eliminar producto del carrito' });
    }
};

