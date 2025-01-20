const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8081', // Spring Boot 서버 주소
                changeOrigin: true,
                pathRewrite: { '^/api': '' }
            }
        },
        port: 8080, // Vue 개발 서버 포트 설정
    }
});
