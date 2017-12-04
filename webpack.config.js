module.exports={
    devtool: 'source-map',
    entry:'./websource/script/code.ts',
    output:{
        filename:'./src/main/resources/static/script/bundle.js'
    },
    resolve: {
        extensions: ['.ts', '.js']
      },
      module: {
        rules: [
          // all files with a `.ts` extension will be handled by `ts-loader`
          { test: /\.ts$/, loader: 'ts-loader' }
        ]
      }
};