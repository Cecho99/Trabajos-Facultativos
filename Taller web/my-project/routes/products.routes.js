const express = require('express');
const router = express.Router();
//const multer = require('multer');
//const path = require('path');

const productsController = require('../controllers/products.controller.js');

router.get('/', productsController.getProducts);

router.get('/product-details/:productId', productsController.getProduct);

router.get('/mycity', productsController.getProductsByCity);

router.post('/:commerceId/products/new', productsController.createProduct);

router.put('/:productId', productsController.updateProduct);

router.delete('/:productId',  productsController.deleteProduct);

module.exports = router;

