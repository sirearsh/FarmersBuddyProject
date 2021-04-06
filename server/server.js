const express = require('express')
const {execSync} = require('child_process')
const app = express()
const port = 8080

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/login', (req,res) => {
  var username = req.param('id');
  var password = req.param('pw');
  // Yet to connect to a userbase.
  let str = execSync(`userlogin ${username} ${password}`).toString();
  res.send(str);
  console.log(username + " " + password+" -> "+str);
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
