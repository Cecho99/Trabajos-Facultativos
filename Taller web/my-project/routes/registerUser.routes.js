const express = require('express');
const router = express.Router();
const usersController = require('../controllers/users.controller.js');

//router.get('/', usersController.renderRegisterUser);

// Renderizar el formulario de registro con datos obtenidos de Google o Facebook
router.get('/', (req, res) => {
  const { email, firstName, lastName, username} = req.query;
  res.render('registerUser', { email, firstName, lastName, username});
});

// Crear un nuevo usuario después de enviar el formulario
router.post('/', usersController.createUser);

module.exports = router;