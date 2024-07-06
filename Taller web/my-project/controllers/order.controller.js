const Product = require('../models/Product.js');
const Order = require('../models/Order.js');


exports.deleteOrder = async (req, res) => {
    const orderId = req.params.orderId;
    try {
        await Order.findByIdAndDelete(orderId);
        res.redirect('/commerces/compras'); 
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error al eliminar la orden' });
    }
};
