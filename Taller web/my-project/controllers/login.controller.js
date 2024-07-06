const Commerce = require('../models/Commerce');
const User = require('../models/User');

exports.getLoginPage = (req, res) => {
    res.render('login');
};

exports.postLogin = async (req, res) => {
    const { email, password } = req.body;
    try {
        let commerce = await Commerce.findOne({ email });
        let user = await User.findOne({ email });

        if (!commerce && !user) {
            return res.render('login', { error: 'Correo electrónico no registrado' });
        }

        if (commerce) {
            const isMatch = await commerce.comparePassword(password);
            if (!isMatch) {
                return res.render('login', { error: 'La contraseña es incorrecta' });
            }

            req.session.uCommerce = {
                id: commerce._id,
                name: commerce.name,
                email: commerce.email
            };

            if (commerce.state){
                return res.redirect('/commerces');
            }else{
                return res.render('login', { error: 'Tu comercio está en estado pendiente' });
            }
            
        }

        if (user) {
            const isMatch = await user.comparePassword(password);
            if (!isMatch) {
                return res.render('login', { error: 'La contraseña es incorrecta' });
            }

            req.session.user = {
                id: user._id,
                name: user.username,
                email: user.email
            };

            if (user.isAdmin){
                return res.redirect('/admin');
            }else{
                return res.redirect('/home');
            }
           
        }

    } catch (error) {
        console.error(error);
        res.render('login', { error: 'Error en el servidor' });
    }
};