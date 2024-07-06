const Product = require('../models/Product.js');
const fs = require('fs');
const path = require('path');
const Commerce = require('../models/Commerce');
const User = require('../models/User');

// Obtener todos los productos con filtros
exports.getProducts = async (req, res) => {
    try {
        const { type } = req.query;
        const filter = {};

        if (type) {
            switch (type) {
                case 'Lácteos y Huevos':
                    filter.type = { $in: ['lácteos', 'huevos'] };
                    break;
                case 'Frutas y Verduras':
                    filter.type = { $in: ['frutas', 'verduras'] };
                    break;
                case 'Carnes y Pescados':
                    filter.type = { $in: ['carnes', 'pollo', 'pescados y mariscos'] };
                    break;
                case 'Panadería y Pastelería':
                    filter.type = { $in: ['panaderia', 'pasteleria', 'harina', 'levadura', 'mezclas para hornear'] };
                    break;
                case 'Bebidas':                  
                    filter.type = { $in: ['bebidas'] };
                    break;
                case 'Alimentos Congelados':
                    filter.type = { $in: ['congelados'] };
                    break;
                case 'Alimentos Secos y Enlatados':
                    filter.type = { $in: ['enlatados', 'pastas', 'arroces',] };
                    break;
                case 'Snacks y Dulces':
                    filter.type = { $in: ['snacks', 'cereales', 'dulces', 'mermeladas', 'miel', 'azúcar'] };
                    break;
                case 'Condimentos y Salsas':
                    filter.type = { $in: ['condimentos', 'salsas', 'aderezos'] };
                    break;
                case 'Aceites y Vinagres':
                    filter.type = { $in: ['aceite', 'vinagre'] };
                    break;
                case 'Tés y Cafés':
                    filter.type = { $in: ['té', 'café'] };
                    break;
                default:
                    filter.type = type.toLowerCase();
                    break;
            }
        }
        
        const products = await Product.find(filter);
        res.render('products', { products, user: req.session.user, selectedType: type });
    } catch (error) {
        res.status(500).json({message: error.message});
    }
};


// Obtener todos los productos de la ciudad del usuario
exports.getProductsByCity = async (req, res) => {
    try {
        // Verifica si req.session.user está definido
        if (!req.session.user) {
            return res.status(401).send('Usuario no autenticado');
        }
        const userId = req.session.user.id;
        const user = await User.findById(userId);

        if (!user) {
            return res.status(404).send('Usuario no encontrado');
        }

        const products = await Product.find()
            .populate({
                path: 'commerce',
                match: { city: user.city }
            })
            .exec();

        const filteredProducts = products.filter(product => product.commerce !== null);

        res.render('products', { products: filteredProducts, user: req.session.user });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.getProduct = async (req, res) => {
    try {
        const { productId } = req.params;
        const product = await Product.findById(productId);
        if (!product) {
            return res.status(404).json({ message: 'Producto no encontrado' });
        }
        res.render('products-details', { product, user: req.session.user });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

exports.createProduct = async (req, res) => {
    try {
        const { name, type, freeGluten, price, caduceDate, stock, description} = req.body;
        const commerceId = req.session.uCommerce.id;
        const photo = '/images/' + req.file.filename; // Ruta de la foto
        const newProduct = new Product({
            name, 
            type,
            freeGluten, 
            price, 
            caduceDate, 
            stock, 
            description, 
            commerce: commerceId, 
            photo
        });
        await newProduct.save();
        res.redirect('/commerces');
    } catch (error) {
        console.log(error);
        res.status(500).json({ message: error.message });
    }
};

// Actualizar un producto por su id
exports.updateProduct = async (req, res) => {
    try {
        const { productId } = req.params;
        await Product.findByIdAndUpdate(productId, req.body, {new: true});
        res.redirect('/commerces');
    } catch (error) {
        res.status(500).json({message: error.message});        
    }    
};

exports.deleteProduct = async (req, res) => {
    try {
        const { productId } = req.params;
        const product = await Product.findById(productId);
        if (!product) {
            return res.status(404).json({ message: 'Producto no encontrado' });
        }
        // Obtener la ruta completa de la imagen a eliminar
        const imagePath = path.join(__dirname, '../public', product.photo);
        // Verificar si el archivo existe antes de intentar eliminarlo
        if (fs.existsSync(imagePath)) {
            // Eliminar el archivo
            fs.unlinkSync(imagePath);
        }
        // Eliminar el producto de la base de datos
        await Product.findByIdAndDelete(productId);
        res.redirect('/commerces');
    } catch (error) {
        res.status(500).json({message: error.message});        
    }    
};