const express = require('express');
const router = express.Router();
const commercesController = require('../controllers/commerces.controller.js');
const productController = require('../controllers/products.controller.js');
const orderController = require('../controllers/order.controller.js');
const upload = require('../multerConfig');


router.get('/compras', commercesController.getCompras);
router.get('/', commercesController.getProductsByCommerce);
router.post('/compras/:orderId', orderController.deleteOrder)
router.put('/:productId', productController.updateProduct);
router.delete('/:productId', productController.deleteProduct);

router.post('/', upload.single('photo'), productController.createProduct);

module.exports = router;
