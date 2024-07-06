const express = require('express');
const router = express.Router();
const commercesController = require('../controllers/commerces.controller.js');
const productController = require('../controllers/products.controller.js');


router.get('/', commercesController.getCommerces);

module.exports = router;
