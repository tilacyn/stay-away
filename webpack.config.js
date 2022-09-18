const path = require("path");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
// const devMode = process.env.NODE_ENV !== "production";

const cssLoaders = (extra) => {
    const loaders = [
        {
            loader: MiniCssExtractPlugin.loader,
            options: {
                hmr: true,
                reloadAll: true,
            },
        },
        'css-loader',
    ]

    if (extra) {
        loaders.push(extra)
    }

    return loaders
}


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
                // test: /\.(jsx|tsx?)$/,
                test: /\.jsx?$/, // определяем тип файлов
                exclude: /(node_modules)/,  // исключаем из обработки папку node_modules
                loader: "babel-loader",   // определяем загрузчик
                options:{
                    presets:[ "@babel/preset-react"]    // используемые плагины
                }
            },
            {
                test: /\.css$/i,
                use: cssLoaders()
            },
        ]
    },
    plugins:  [new MiniCssExtractPlugin()],
};