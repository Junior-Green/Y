const express = require('express');
const router = express.Router();

// Serve index.html by default
app.get('*', (req, res) => {
    res.sendFile(path.join('dist', 'index.html'));
  });

router.get('/', (req, res) => {
  res.send('Home page');
});

router.get('/users', (req, res) => {
  res.send('Users page');
});


module.exports = router;