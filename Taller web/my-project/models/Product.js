const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Defino el esquema del producto
const productSchema = new Schema({
    name: { type: String, required: true},    
    type: [{ 
        type: String, 
        required: true, 
        enum: [
            'lácteos', 'huevos',
            'frutas', 'verduras',
            'carnes', 'pollo', 'pescados y mariscos',
            'panaderia', 'pasteleria', 'harina', 'levadura', 'mezclas para hornear',
            'bebidas', 
            'congelados',
            'enlatados', 'pastas', 'arroces',
            'snacks', 'cereales','dulces', 'mermeladas', 'miel', 'azúcar',
            'condimentos', 'salsas', 'aderezos',
            'aceite', 'vinagre', 
            'té', 'café',
          ]
    }],
    freeGluten: { type: Boolean, required: true }, //con-sin T.A.A.C.
    price: { type: Number, required: true },
    caduceDate: { type: Date, required: true },
    stock: { type: Number, required: true },
    description: { type: String, required: false },
    commerce: { type: String, required: true },
    photo:{ type: String, required: true }
});

// Creo el modelo del producto basado en el esquema
const Product = mongoose.model('product', productSchema);

// Exporto el modelo
module.exports = Product;