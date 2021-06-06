const path = require("path");
const webpack = require("webpack");

module.exports = {
    mode: 'production',
    entry: {
        app: "./app.js"
    },
    output: {
        path: __dirname,
        filename: "[name].bundle.js"
    }
};
