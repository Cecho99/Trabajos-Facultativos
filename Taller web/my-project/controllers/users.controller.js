const mongoose = require('mongoose');
const User = require('../models/User.js');

// Crear un nuevo usuario después de llenar el formulario
exports.createUser = async (req, res) => {
    try {
        const {email, username, password, firstName, lastName, dateOfBirth, address, postal_code, city, province, country} = req.body;
        // Buscar si el usuario ya existe por nombre de usuario o correo electrónico
        const existingUser = await User.findOne({ email });

        if (existingUser) {
            return res.status(400).json({ message: 'El correo electrónico ya está registrado' });
        }

        const newUser = new User({
            email,
            username,
            password,
            firstName,
            lastName,
            dateOfBirth,
            isOAuth: false,
            address,
            postal_code,
            city,
            province,
            country
        });

        await newUser.save();
        res.redirect('/');
    } catch (error) {
        console.log(error);
        res.status(500).json({ message: error.message });
    }
};