const mongoose = require('mongoose');
const CarSchema = new mongoose.Schema({
    name: {
        type: String,
        require: true
    },
    price: {
        type: Number,
        require: true
    },
    color:{
        type:String,
        require: true
    },
    img:{
        type:String,
        require: true
    },
    quantity: {
        type: Number,
        require: true
    },
    description: {
        type: String,
        require: true
    },
})

const CarModel = new mongoose.model('cars',CarSchema);
module.exports = CarModel;