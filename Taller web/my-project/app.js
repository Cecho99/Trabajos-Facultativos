require('dotenv').config();

var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var methodOverride = require('method-override');
var multer = require('multer');
var session = require('express-session');
const sessionSecret = process.env.SESSION_SECRET || 'default_secret';


var indexRouter = require('./routes/index');
var productsRouter = require('./routes/products.routes');
var commerceRouter = require('./routes/commerces.routes');
var loginRouter = require('./routes/login.routes');
var registerRouter = require('./routes/register.routes');
var registerUserRouter = require('./routes/registerUser.routes');
var registerCommerceRouter = require('./routes/registerCommerce.routes');
var carritoRouter = require('./routes/carrito.routes');
var storesRouter = require('./routes/stores.routes');
var adminRouter = require('./routes/admin.routes');
var passport = require('./passport-config');
var authRouter = require('./routes/auth.routes');


const mongoose = require('mongoose');
const uri = process.env.MONGODB_URI;


var app = express();

// Conectar a la base de datos
(async () => {
  try {
      await mongoose.connect(uri);
      console.log('Successfully connected to database');
  } catch (err) {
      console.error('Error:', err);
  }
})();

app.use(session({
  secret: sessionSecret, 
  resave: false,
  saveUninitialized: false,
  cookie: { secure: false } // false -> utiliza HTTP y TRUE -> utiliza HTPPS (necesario cuando se despliegue la app)
}));

app.use(passport.initialize());
app.use(passport.session());

// middlewares
app.use(express.json());
app.use(methodOverride('_method'));
app.use((req, res, next) => {
  res.locals.user = req.session.user || null;
  next();
});
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/home', indexRouter);
app.use('/products', productsRouter);
app.use('/commerces', commerceRouter);
app.use('/', loginRouter);
app.use('/register', registerRouter);
app.use('/registerUser', registerUserRouter);
app.use('/registerCommerce', registerCommerceRouter);
app.use('/carrito', carritoRouter);
app.use('/stores', storesRouter);
app.use('/admin', adminRouter);
app.use('/auth', authRouter);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  res.status(err.status || 500);
  res.render('error');
});


module.exports = app;