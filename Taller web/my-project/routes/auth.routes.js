const express = require('express');
const router = express.Router();
const passport = require('passport');

// Ruta para Google OAuth
router.get('/google', passport.authenticate('google', { scope: ['profile', 'email'] }));

router.get('/google/callback', 
  passport.authenticate('google', { failureRedirect: '/' }), 
  (req, res) => {
    req.logIn(req.user, (err) => {
      if (err) {
        console.log('Error al iniciar sesión:', err);
        return res.redirect('/');
      }
      req.session.user = {
        id: req.user._id,
        name: req.user.username,
        email: req.user.email
      };
      res.redirect('/home');
    });
  }
);

// Ruta para Facebook OAuth
router.get('/facebook', passport.authenticate('facebook', { scope: ['email'] }));

router.get('/facebook/callback', 
  passport.authenticate('facebook', { failureRedirect: '/' }), 
  (req, res) => {
    req.logIn(req.user, (err) => {
      if (err) {
        console.log('Error al iniciar sesión:', err);
        return res.redirect('/');
      }
      req.session.user = {
        id: req.user._id,
        name: req.user.username,
        email: req.user.email
      };
      res.redirect('/home');
    });
  }
);

module.exports = router;