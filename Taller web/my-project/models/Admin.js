const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// Defino el esquema del usuario
const adminSchema = new Schema({
      username: { // El nombre del usuario es único, requerido y con un mínimo de 3 caracteres.
        type: String,
        required: true,
        unique: true,
        trim: true, // Elimina los espacios en blanco al principio y al final de la cadena de string
        minlength: 3
      },
      password: { // Contraseña requerida, con un mínimo de 6 caracteres.
        type: String,
        required: true,
        minlength: 6
      },
      isAdmin: { // Por defecto siempre es false
        type: Boolean,
        default: true
      },
      
    });


// Creo el modelo del producto basado en el esquema
const Admin = mongoose.model('admin', adminSchema);

// Exporto el modelo
module.exports = Admin;