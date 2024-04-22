const express = require('express');
const app = express();
const path = require('path');
const routes = require('./routes');
const bodyParser = require('body-parser');
const errorHandler = require('error-handler');

// Serve static files from the 'dist' directory (where Vue builds to)
app.use(express.static('./dist'));

app.use(bodyParser.json()); // for parsing application/json
app.use(errorHandler());

app.use('/', routes);


const port = 3000;
app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});