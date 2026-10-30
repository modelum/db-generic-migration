const mongoose = require('mongoose');

const PaymentMethods = new mongoose.Schema({
	methodName: {
		type: String,
		required: true,
		unique: false
	},
	methodInfo: {
		type: String,
		required: true,
		unique: false
	},
	id_id: {
		type: Number,
		required: true,
		unique: true
	},
	PaymentMethods_id: {
		type: Number,
		required: true,
		unique: true
	}
})
const AppUser = new mongoose.Schema({
	name: {
		type: String,
		required: true,
		unique: false
	},
	email: {
		type: String,
		required: true,
		unique: false
	},
	password: {
		type: String,
		required: true,
		unique: false
	},
	address: {
		type: String,
		required: true,
		unique: false
	},
	country: {
		type: String,
		required: true,
		unique: false
	},
	registrationDate: {
		type: String,
		required: true,
		unique: false
	},
	storeValuation: {
		type: Number,
		required: true,
		unique: false
	},
	premium: {
		type: Number,
		required: true,
		unique: false
	},
	date_premium: {
		type: String,
		required: true,
		unique: false
	},
	premium_fee: {
		type: Number,
		required: true,
		unique: false
	},
	card_number: {
		type: String,
		required: true,
		unique: false
	},
	AppUser_id: {
		type: Number,
		required: true,
		unique: true
	},
	PaymentMethods: [PaymentMethods],
	appUsers: {
		type: [Number],
		ref: 'AppUser',
		required: true
	},
	userOrders: {
		type: [String],
		ref: 'UserOrder',
		required: true
	}
})
const Product = new mongoose.Schema({
	name: {
		type: String,
		required: true,
		unique: false
	},
	price: {
		type: Number,
		required: true,
		unique: false
	},
	description: {
		type: String,
		required: true,
		unique: false
	},
	imageURL: {
		type: String,
		required: true,
		unique: false
	},
	manufacturer: {
		type: String,
		required: true,
		unique: false
	},
	stock: {
		type: Number,
		required: true,
		unique: false
	},
	Product_id: {
		type: Number,
		required: true,
		unique: true
	},
	items: {
		type: [String],
		ref: 'Item',
		required: true
	}
})
const Category = new mongoose.Schema({
	description: {
		type: String,
		required: true,
		unique: false
	},
	Category_id: {
		type: Number,
		required: true,
		unique: true
	},
	products: {
		type: [Number],
		ref: 'Product',
		required: true
	}
})
const UserOrder = new mongoose.Schema({
	orderDate: {
		type: String,
		required: true,
		unique: false
	},
	totalPrice: {
		type: Number,
		required: true,
		unique: false
	},
	UserOrder_id: {
		type: String,
		required: true,
		unique: true
	},
	items: {
		type: [String],
		ref: 'Item',
		required: true
	},
	discounts: {
		type: [Number],
		ref: 'Discount',
		required: true
	}
})
const Item = new mongoose.Schema({
	amount: {
		type: Number,
		required: true,
		unique: false
	},
	subtotal: {
		type: Number,
		required: true,
		unique: false
	},
	Item_id: {
		type: String,
		required: true,
		unique: true
	}
})
const Discount = new mongoose.Schema({
	description: {
		type: String,
		required: true,
		unique: false
	},
	discountRatio: {
		type: Number,
		required: true,
		unique: false
	},
	Discount_id: {
		type: Number,
		required: true,
		unique: true
	}
})
const RelatedProducts = new mongoose.Schema({
	affinity: {
		type: Number,
		required: true,
		unique: false
	},
	RelatedProducts_id: {
		type: Number,
		required: true,
		unique: true
	},
	product_id_fk: {
		type: ,
		ref: 'Product',
		required: true
	},
	product_related_id_fk: {
		type: ,
		ref: 'Product',
		required: true
	}
})
