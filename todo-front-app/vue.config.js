const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: ["vuetify"],
  devServer: {
    port: 3000,
    proxy: {
      "^/tasks": {
        target: "http://localhost:8080",
        ws: true,
        pathRewrite: {
          "^/tasks": "/tasks",
        },
      },
    },
  },
});
