const express = require("express");
const app = express();
const jsonParser = express.json();
  
app.use(express.static(__dirname + "/public"));

const { Client } = require("pg");


const credentials = {
    user: 'lwbrstmokfkeih',
    host: 'ec2-99-80-170-190.eu-west-1.compute.amazonaws.com',
    database: 'dsgq5lbr01qlf',
    password: '3d49ead7be2706fbc705f562944e6be2d18f5a43913b813dd0704cbdfb68bca6',
    port: 5432,
     ssl: { rejectUnauthorized: false }
  }

function getClient(){
    const client = new Client(credentials); 
    
    client.connect(function(err) {
        console.log(client)
        if (err)  throw err;
        console.log("Connected!");
    });
    
    return client;
}
app.get("/Dish/Get",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from "Dish"');
    
    await client.end();
    
    res.send(rows);
});

app.post("/Dish/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Name, Cost} = req.body;

    const client = getClient();

    await client.query(`insert into "Dish" ("Name","Cost") values ($1,$2)`,[Name,Cost])
    
    res.send(req.body);
});
app.put("/Dish/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Name,Cost, Id} = req.body;
    
    console.log(`${Name} + ${Cost} + ${Id} + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"`);

    const client = getClient();

    await client.query(`update "Dish" set "Name"=$1,"Cost"=$2 where "Id" = $3`,[Name,Cost,Id])
    
    res.send(req.body);
});
app.delete("/Dish/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Id} = req.body;

    const client = getClient();

    await client.query(`delete from "Dish" where "Id" = $1`,[Id])
    
    res.send(req.body);
});

app.get("/User/Get",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from "User"');
    
    await client.end();
    
    res.send(rows);
});

app.post("/User/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Login, Password, FullName, Role} = req.body;

    const client = getClient();

    await client.query(`insert into "User" ("Login","Password", "FullName", "Role") values ($1,$2,$3,$4)`,[Login, Password, FullName, Role]);
    
    res.send(req.body);
});
app.put("/User/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Login, Password, FullName, Role, Id} = req.body;

    const client = getClient();

    await client.query(`update "User" set "Login"=$1,"Password"=$2, "FullName" = $3, "Role"=$4 where "Id" = $5`,[Login, Password, FullName, Role, Id])
    
    res.send(req.body);
});
app.delete("/User/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Id} = req.body;

    const client = getClient();

    await client.query(`delete from "User" where "Id" = $1`,[Id])
    
    res.send(req.body);
});
app.get("/Order/Get",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from "Order"');
    
    await client.end();
    
    res.send(rows);
});

app.post("/Order/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Status, Date, Address, CustomerId} = req.body;

    const client = getClient();

    await client.query(`insert into "Order" ("Status","Date", "Address", "CustomerId") values ($1,$2,$3,$4)`,[Status,Date, Address, CustomerId]);
    
    res.send(req.body);
});
app.put("/Order/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {EmployeeId, Id} = req.body;

    const client = getClient();

    await client.query(`update "Order" set "EmployeeId"=$1 where "Id" = $2`,[EmployeeId, Id])
    
    res.send(req.body);
});
app.delete("/Order/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Id} = req.body;

    const client = getClient();

    await client.query(`delete from "Order" where "Id" = $1`,[Id])
    
    res.send(req.body);
});
app.get("/OrderedDish/Get",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from "OrderedDish"');
    
    await client.end();
    
    res.send(rows);
});

app.post("/OrderedDish/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {DishId, OrderId} = req.body;

    const client = getClient();

    await client.query(`insert into "OrderedDish" ("OrderId","DishId") values ($1,$2)`,[OrderId, DishId]);
    
    res.send(req.body);
});
app.delete("/OrderedDish/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    const {Id} = req.body;

    const client = getClient();

    await client.query(`delete from "OrderedDish" where "Id" = $1`,[Id])
    
    res.send(req.body);
});

app.listen(process.env.PORT || 3000);