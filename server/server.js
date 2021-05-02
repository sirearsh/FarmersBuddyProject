const express = require('express')
const {execSync} = require('child_process')
const path = require('path')
const app = express()
const port = process.env.PORT || 8080
var count = 0
const session = Date.now().valueOf();

const onlineUsers=new Map();
function makeOnline(id, securityID) {
    onlineUsers.set(securityID,id);
}
function checkUser(securityID) {
    if (onlineUsers.has(securityID))
        return onlineUsers.get(securityID);
    else
        return -1;
}
function logout(securityID) {
    return onlineUsers.delete(securityID);
}

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/login', (req,res) => {
  var username = req.param('id');
  var password = req.param('pw');
  let str = execSync(`userlogin ${username} ${password}`).toString();
  let obj = JSON.parse(str);
  obj['securityID']=(count+session);
  makeOnline(obj['id'],obj['securityID']);
  count+=1
  res.send(obj);
  console.log(username + " " + password+" -> ");
  console.log(obj);
})

app.get('/logout', (req,res)=> {
  var securityID=parseInt(req.param('sid'))
  res.send({"query":logout(securityID)});

})

app.get('/info', (req,res) => {
  var file_name=req.param('id')
  res.header("Content-Type", 'application/json')
  res.sendFile(path.resolve('info/'+file_name+'.json'))
})

app.get('/check_user', (req,res)=>{
  var securityID=parseInt(req.param('sid'))
  let id=checkUser(securityID)
  if (id==-1) {
    res.send({"status":false})
  } else {
    res.send(JSON.parse(execSync(`getObjectAt ${id}`)))
  }
})

const es_data = require('./expert_system/data.json')

app.get('/ai', (req, res) => {
  var cityName = req.param('city')
  res.send(es_data[cityName] || '{}')
  console.log(cityName+" : "+(es_data[cityName] || '{}'))
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
