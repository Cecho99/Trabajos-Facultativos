const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const bcrypt = require('bcrypt');

const userSchema = new Schema({
  email: {
    type: String,
    required: true,
    unique: true,
    match: /.+@.+..+/
  },
  username: {
    type: String,
    required: true,
    unique: true,
    trim: true,
    minlength: 3
  },
  password: {
    type: String,
    required: function() {
      return !this.isOAuth; // Si es OAuth, la contraseña no es requerida
    },
    validate: {
      validator: function(value) {
        // Solo validar la longitud si la contraseña no está vacía
        return this.isOAuth || value.length >= 6;
      },
      message: 'La contraseña debe tener al menos 6 caracteres.'
    }
  },
  firstName: {
    type: String,
    required: true,
    trim: true
  },
  lastName: {
    type: String,
    required: true,
    trim: true
  },
  dateOfBirth: {
    type: Date,
    required: false
  },
  isAdmin: {
    type: Boolean,
    default: false
  },
  createdAt: {
    type: Date,
    default: Date.now
  },
  updatedAt: {
    type: Date,
    default: Date.now
  },
  idRole: {
    type: String,
    required: true,
    default: "buyer"
  },
  isOAuth: {
    type: Boolean,
    default: false // Campo para indicar si es un usuario OAuth
  },
  address: {
    type: String,
    required: false,
  },
  postal_code: {
      type: Number,
      required: false,
  },
  city: {
      type: String,
      required: false,
  },
  province: {
      type: String,
      required: false,
  },
  country: {
      type: String,
      required: false,
  }
});

// Middleware para actualizar el campo updatedAt cada vez que se guarda el documento
userSchema.pre('save', function(next) {
  this.updatedAt = Date.now();
  next();
});

// Middleware para hashear la contraseña antes de guardarla
userSchema.pre('save', async function (next) {
  if (!this.isModified('password')) {
      return next();
  }

  const salt = await bcrypt.genSalt(10);
  this.password = await bcrypt.hash(this.password, salt);
  next();
});

// Método para comparar contraseñas
userSchema.methods.comparePassword = function(candidatePassword) {
  return bcrypt.compare(candidatePassword, this.password);
};

// Creo el modelo del usuario basado en el esquema
const User = mongoose.model('User', userSchema);

// Exporto el modelo
module.exports = User;