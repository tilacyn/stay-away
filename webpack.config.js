const path = require("path")

module.exports = {
    entry: "./src/main/resources/app.jsx",
    output : {
        path: path.resolve(__dirname, "public"),
        publicPath: "/public/",
        filename: "bundle.js"
    },
    module:{
        rules:[   //загрузчик для jsx
            {
                test: /\.jsx?$/, // определяем тип файлов
                exclude: /(node_modules)/,  // исключаем из обработки папку node_modules
                loader: "babel-loader",   // определяем загрузчик
                options:{
                    presets:[ "@babel/preset-react"]    // используемые плагины
                }
            }
        ]
    }
}