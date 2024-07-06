const Commerce = require('../models/Commerce.js');

exports.getCommerces = async (req, res) => {
    try {
        const commerces = await Commerce.find({ state: false });
        res.render('admin', { commerces });
    } catch (error) {
        res.status(500).json({message: error.message});
    }
};

exports.updateCommerce = async (req, res) => {
    try {
        const { commerceId } = req.params;
        await Commerce.findByIdAndUpdate(commerceId, req.body, {new: true});
        res.redirect('/admin');
    } catch (error) {
        res.status(500).json({message: error.message});        
    }    
};

exports.deleteCommerce = async (req, res) => {
    try {
        const { commerceId } = req.params;
        await Commerce.findByIdAndDelete(commerceId);
        res.redirect('/admin');
    } catch (error) {
        res.status(500).json({message: error.message});        
    }    
};
