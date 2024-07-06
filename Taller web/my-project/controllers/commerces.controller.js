const Commerce = require('../models/Commerce.js');
const Product = require('../models/Product.js');
const User = require('../models/User.js');
const Order = require('../models/Order.js');

// Crear un nuevo comercio
exports.createCommerce = async (req, res) => {
    try {
        const {name, password, email, address, postal_code, city, province, country} = req.body;
        const newCommerce = new Commerce({name, password, email, address, postal_code, city, province, country});
        console.log('Este es el nuevo comercio: ', newCommerce);
        await newCommerce.save();
        res.redirect('/');
    } catch (error) {
        console.log(error);
        res.status(500).json({message: error.message});
    }
};


// Método para obtener productos por id_commerce
exports.getProductsByCommerce = async (req, res) => {
    try {
        const commerceId = req.session.uCommerce.id;
        const products = await Product.find({ commerce: commerceId });
        res.render('commerces', { products, user: req.session.uCommerce });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
};


// Método para obtener comercios
exports.getCommerces = async (req, res) => {
    try {
        const stores = await Commerce.find();
        const googleMapsApiKey = process.env.GOOGLE_MAPS_API_KEY;
        res.render('stores', { 
            stores, 
            user: req.session.user, 
            googleMapsApiKey
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
};


// Método para obtener las compras de los usuarios
exports.getCompras = async (req, res) => {
    try {
        const commerceId = req.session.uCommerce.id;
        const orders = await Order.find({ commerce: commerceId })
            .populate('user', 'firstName lastName')  
            .populate('product', 'name price'); 

        res.render('compras', { orders, user: req.session.uCommerce });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message});
    }
}


exports.updateState = async (req, res) => {
    try {
        const { commerceId } = req.params;
        const commerce = await Commerce.findById(commerceId);
        if (!commerce) {
            return res.status(404).json({ message: "Commerce not found" });
        }
        commerce.state = true;
        await commerce.save();
        res.redirect('/admin');
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
};