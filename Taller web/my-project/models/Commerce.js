const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const bcrypt = require('bcrypt');

// Defino el esquema del comercio
const commerceSchema = new Schema({
    name: {
        type: String,
        required: true,
        trim: true, // Elimina los espacios en blanco al principio y al final de la cadena de string
    },
    password: { // Contraseña requerida, con un mínimo de 6 caracteres.
        type: String,
        required: true,
        minlength: 6
    },
    email: { // Correo electrónico único, requerido, y debe coincidir con un patrón de correo válido.
        type: String,
        required: true,
        unique: true,
        match: /.+@.+..+/
    },
    address: {
        type: String,
        required: true,
    },
    postal_code: {
        type: Number,
        required: true,
    },
    city: {
        type: String,
        required: true,
    },
    province: {
        type: String,
        required: true,
    },
    country: {
        type: String,
        required: true,
    },
    idRole: {
        type: String,
        required: true,
        default: "seller"
    },
    state: 
    {
        type: Boolean,
        required: true,
        default: false
    },
})

// Middleware para hashear la contraseña antes de guardarla
commerceSchema.pre('save', async function(next) {
    if (!this.isModified('password')) {
      return next();
    }

    const salt = await bcrypt.genSalt(10);
    this.password = await bcrypt.hash(this.password, salt);
    next();
});
  
  // Método para comparar contraseñas
commerceSchema.methods.comparePassword = function(candidatePassword) {
    return bcrypt.compare(candidatePassword, this.password);
};
  
// Creo el modelo del comercio basado en el esquema
const Commerce = mongoose.model('Commerce', commerceSchema);
  
// Exporto el modelo
module.exports = Commerce;