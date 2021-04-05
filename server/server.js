const express = require('express')
const {execSync} = require('child_process')
const app = express()
const port = 8080

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/custom', (req,res) => {
  res.send('Dhruv is dumb!')
})

app.get('/login', (req,res) => {
  var username = req.param('id');
  var password = req.param('pw');
  // Yet to connect to a userbase.
  if (username == "dhruv" && password == "12345") {
    res.send("successful");
  } else {
    res.send(400);
  }
  console.log(username + " " + password);
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
