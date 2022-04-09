const express = require("express");
const app = express();
const jsonParser = express.json();
  
app.use(express.static(__dirname + "/public"));

const { Client } = require("pg");


const credentials = {
    user: 'hvnceixkfztruv',
    host: 'ec2-34-247-72-29.eu-west-1.compute.amazonaws.com',
    database: 'd22ko0vn8djg',
    password: '6836d1d29b64c2a9fc901c7428921b569cf183d5236fc3718a9502acf5334adf',
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
app.get("/Dish/GetAll",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select *  from getAll(null::"Dish")');
    
    await client.end();
    
    res.send(rows);
});

app.get("/Dish/Get",async function(req, res){
    
    const client = getClient();

    let {Id} = req.query;

    let {rows} = await client.query(`select * from getById(null::"Dish", ${Id})`);
    
    await client.end();
    
    res.send(rows);
});

app.post("/Dish/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Name, Cost} = req.body;
        
        const client = getClient();
        
        await client.query(`insert into "Dish" ("Name","Cost") values ($1,$2)`,[Name,Cost])
        
    } catch(err){

    }
});
app.put("/Dish/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Name,Cost, Id} = req.body;
        
        const client = getClient();
        
        await client.query(`update "Dish" set "Name"=$1,"Cost"=$2 where "Id" = $3`,[Name,Cost,Id])
        
    } catch(err){

    }
});
app.delete("/Dish/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Id} = req.body;
        
        const client = getClient();
        
        await client.query(`delete from "Dish" where "Id" = $1`,[Id])
    } catch(err){

    }
});

app.get("/User/GetAll",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from getAll(null::"User")');
    
    await setUserFields(rows, client);

    await client.end();
    
    res.send(rows);
});

app.get("/User/Get",async function(req, res){
    
    const client = getClient();

    let {Id} = req.query;

    let {rows} = await client.query(`select * from getById(null::"User", ${Id})`);
    
    await setUserFields(rows, client);

    await client.end();
    
    res.send(rows);
}); 

app.post("/User/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try {

        const {Login, Password, FullName, Role} = req.body;
        
        const client = getClient();
        
        await client.query(`insert into "User" ("Login","Password", "FullName", "RoleId") values ($1,$2,$3,$4)`,[Login, Password, FullName, Role]);
        
        res.sendStatus(200);
    } catch(err){
        res.send("пизда рулю");
    }
});
app.put("/User/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Login, Password, FullName, Role, Id} = req.body;
        
        const client = getClient();
        
        await client.query(`update "User" set "Login"=$1,"Password"=$2, "FullName" = $3, "Role"=$4 where "Id" = $5`,[Login, Password, FullName, Role, Id])
        
        res.sendStatus(200);
    } catch(err){
        res.send("пизда рулю");
    }   
});
app.delete("/User/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    try{
        const {Id} = req.body;
        
        const client = getClient();
        
        await client.query(`delete from "User" where "Id" = $1`,[Id])
        res.sendStatus(200);
    } catch(err){
        res.send("пизда рулю");
    }        
});
app.get("/Order/GetAll",async function(req, res){
    
    const client = getClient();
    
    let {rows} = await client.query('select * from getById(null::"Order")');
    
    await setOrderFields(rows, client);
    
    await client.end();

    res.send(rows);
});

app.get("/Order/Get",async function(req, res){
    
    const client = getClient();

    let {Id} = req.query;

    let {rows} = await client.query(`select * from getById(null::"Order",${Id})`);
    
    await setOrderFields(rows,client);

    await client.end();

    res.send(rows);
});

app.post("/Order/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Status, Date, Address} = req.body;
        
        const CustomerId = req.body.Customer.Id;
        
        const client = getClient();
        
        await client.query(`insert into "Order" ("Status","Date", "Address", "CustomerId") values ($1,$2,$3,$4)`,[Status,Date, Address, CustomerId]);
        
    } catch(err){
        
    }
    
});
app.put("/Order/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Id, SubmittedAt, Status, Address} = req.body;
        
        const EmployeeId = req.body.CurrentEmployee.Id; 

        const CustomerId = req.body.Customer.Id; 

        const client = getClient();
        
        await client.query(`select updateOrderByOrderId($1,$2,$3,$4,$5,$6)`,[Id, SubmittedAt, Status, Address, EmployeeId, CustomerId]);
        
        const order = req.body;

        await updateOrderedDishes(order,client);

        res.sendStatus(200);
        
    } catch(err){
        
    }    
});

app.delete("/Order/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Id} = req.body;
        
        const client = getClient();
        
        await client.query(`delete from "Order" where "Id" = $1`,[Id])
        
    }catch(err){
        
    }    
});

async function setOrderFields(rows, client){

    await setDishes(rows, client);
    await setUser(rows, client);
}

async function setDishes(rows, client){

    for(const order of rows)  {
        order.Dishes = (await client.query(`select * from getDishesByOrderId(${order.Id})`)).rows;
    };

    return rows;
}

async function setUser(rows, client){

    for(const order of rows)  {

        order.Customer = (await client.query(`select * from getById(null::"User", ${order.CustomerId})`)).rows;
        
        order.CurrentEmployee = (await client.query(`select * from getById(null::"User", ${order.EmployeeId})`)).rows;

        delete order.EmployeeId;

        delete order.CustomerId;
    };

    return rows;
}

async function setUserFields(rows, client){
    await setRole(rows, client);
}

async function setRole(rows, client){

    for(const user of rows)  {
        user.Role = (await client.query(`select * from getById(null::"Role",${user.RoleId})`)).rows;
        
        delete user.RoleId;
    };

    return rows;
}

async function updateOrderedDishes(order, client){
    
    let {Id} = order;
    
    await client.query(`select deleteOrderedDishesByOrderId(${Id})`); 
    
    for(const dish of order.Dishes){
        await client.query(`select insertOrderedDish(${Id}, ${dish.Id})`)
    }
}
app.listen(process.env.PORT || 3000);