const path = require('path');

module.exports = {
    devServer: {
        port: 3000,
        host: "localhost",
        allowedHosts: "all",
    },
    configureWebpack: {
        resolve: {
            alias: {
                '@': path.resolve(__dirname, 'src')
            }
        }
    }
};
