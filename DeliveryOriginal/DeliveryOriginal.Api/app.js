const express = require("express");
const app = express();
const jsonParser = express.json();
const {imageUrl, uploads, destroyImage} = require('./cloudinary')
const {getClient} = require('./db');
app.use(express.static(__dirname + "/public"));
const { Guid } = require("js-guid");


app.post("/GetImageUrl",jsonParser, async function(req, res){
    try{
        const {ImageString} = req.body;
        
        const PublicId = Guid.newGuid().toString()

        uploads(ImageString, PublicId);
        
        res.send(imageUrl+PublicId);

    } catch(err){
        res.send(err.message); 
    }
});

app.delete("/DeleteImage",jsonParser, async function(req, res){
    try{
        const {ImageUrl} = req.body;

        destroyImage(ImageUrl);

        res.sendStatus(200);

    } catch(err){
        res.send(err.message); 
    }
});

app.get("/Dish/GetAll",async function(req, res){
    
    try{

        const client = getClient();
        
        let {rows} = await client.query('select *  from getAll(null::"Dish")');
    
        await client.end();
    
        res.send(rows);

    } catch(err){
        res.send(err.message); 
    }
});

app.get("/Dish/Get",async function(req, res){
    
    try{

        const client = getClient();
        
        let {Id} = req.query;
        
        let {rows} = await client.query(`select * from getById(null::"Dish", ${Id})`);
        
        await client.end();
        
        res.send(rows[0]);

    } catch(err){
        res.send(err.message);  
    }
});

app.get("/Dish/GetDishesByCategory",async function(req, res){
    
    try{
        const client = getClient();
        
        let {CategoryId} = req.query;

        let {rows} = (await client.query(`select * from getDishesByCategory(${CategoryId})`));

        await setCategory(client, rows);

        await client.end();

        res.send(rows);

    } catch(err){
        res.send(err.message);
    }
});

app.post("/Dish/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Name, Cost, Description, ImageUrl} = req.body;
        
        const client = getClient();
        
        const CategoryId = req.body.Category?.Id;
        
        await client.query(`select insertDish($1,$2,$3,$4,$5)`,[Name,Cost,CategoryId,Description, ImageUrl])
        
        await client.end();

        res.sendStatus(200)
    } catch(err){
        res.send(err.message);
    }
});
app.put("/Dish/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Name,Cost, Id, Description, ImageUrl} = req.body;
        
        const CategoryId = req.body.Category?.Id;

        const client = getClient();
        
        // if(ImageUrl){
        //     let {rows} = await client.query(`select * from getById(null::"Dish", ${Id})`);

        //     destroyImage(rows[0].ImageUrl);
        // }
        
        await client.query(`select updateDish($1,$2,$3,$4, $5, $6)`,[Id, Name, Cost, CategoryId, Description, ImageUrl]);

        await client.end();
        
        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
    }
});
app.delete("/Dish/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Id} = req.query;
        
        const client = getClient();

        let {rows} = await client.query(`select * from getById(null::"Dish", ${Id})`);
        
        deleteImage(rows[0].ImageUrl);
        
        await client.query(`delete from "Dish" where "Id" = $1`,[Id])

        await client.end();

        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
    }
});

app.get("/User/GetAll",async function(req, res){
    
    const client = getClient();

    let {rows} = await client.query('select * from getAll(null::"User")');
  
    await client.end();
    
    res.send(rows);
});

app.get("/User/Get",async function(req, res){
    try{

        const client = getClient();
        
        let {Id} = req.query;

        let {rows} = await client.query(`select * from getById(null::"User", ${Id})`);
  
        await client.end();
    
        res.send(rows[0]);

    } catch(err){
        res.send(err.message);
    }
}); 

app.post("/User/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try {

        const {Login, Password, FullName, Role} = req.body;
        
        const client = getClient();

        await client.query(`insert into "User" ("Login","Password", "FullName", "Role") values ($1,$2,$3,$4)`,[Login, Password, FullName, Role]);
        

        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
    }
});
app.put("/User/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Login, Password, FullName, Role, Id} = req.body;
        
        const client = getClient();
        
        await client.query(`select updateUser($5,$1,$2,$3,$4)`,[Login, Password, FullName, Role, Id])
        
        await client.end();

        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
    }   
});

app.delete("/User/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);

    try{
        const {Id} = req.query;
        
        const client = getClient();

        await client.query(`select deleteUser(${Id})`)

        await client.end();

        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
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

    let {rows} = await client.query(`select * from getById(null::"Order",$1)`,[Id]);
    
    await setOrderFields(rows,client);

    await client.end();

    res.send(rows[0]);
});

app.post("/Order/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Status, Date, Address} = req.body;
        
        const CustomerId = req.body.Customer.Id;
        
        const client = getClient();
        
        await client.query(`insert into "Order" ("Status","Date", "Address", "CustomerId") values ($1,$2,$3,$4)`,[Status,Date, Address, CustomerId]);
        
        await client.end();

    } catch(err){
        res.send(err.message);
    }
    
});
app.put("/Order/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Id, SubmittedAt, Status, Address} = req.body;
        
        const EmployeeId = req.body.CurrentEmployee?.Id; 

        const CustomerId = req.body.Customer?.Id; 

        const client = getClient();
        
        await client.query(`select updateOrderByOrderId($1,$2,$3,$4,$5,$6)`,[Id, SubmittedAt, Status, Address, EmployeeId, CustomerId]);

        if(!req.body.Dishes){
            await updateOrderedDishes(req.body,client);
        }

        await client.end();

        res.sendStatus(200);
    } catch(err){
        res.send(err.message);
    }    
});

app.delete("/Order/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Id} = req.query;
        
        const client = getClient();
        
        await client.query(`delete from "Order" where "Id" = $1`,[Id])
        
        await client.end()

        res.sendStatus(200);

    }catch(err){
        res.send(err.message);
    }    
});

app.get("/Category/GetAll",async function(req, res){
    
    const client = getClient();
    
    let {rows} = await client.query('select * from getById(null::"Category")');
    
    await client.end();

    res.send(rows);
});

app.get("/Category/Get",async function(req, res){
    
    const client = getClient();

    let {Id} = req.query;

    let {rows} = await client.query(`select * from getById(null::"Category",$1)`,[Id]);
  
    await client.end();

    res.send(rows[0]);
});

app.post("/Category/Add", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Name} = req.body;
        
        const client = getClient();
        
        console.log(Name)
        
        await client.query(`select insertCategory($1)`,[Name]);
        
        await client.end();

        res.sendStatus(200);

    } catch(err){
        res.send(err.message);
    }
});

app.put("/Category/Update", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{

        const {Id, Name} = req.body;
    
        const client = getClient();
        
        await client.query(`select updateCategory($1,$2)`,[Id, Name]);

        await client.end();

        res.sendStatus(200);

    } catch(err){
        res.send(err.message);
    }    
});

app.delete("/Category/Delete", jsonParser, async function (req, res) {
    
    if(!req.body) return res.sendStatus(400);
    
    try{
        
        const {Id} = req.query;
        
        const client = getClient();
    
        let {rows} = await client.query(`select * from getById(null::"Category", ${Id})`);
        
        deleteImage(rows[0].ImageUrl);

        await client.query(`select deleteCategory($1)`,[Id])

        await client.end();
        
        res.sendStatus(200);

    }catch(err){
        res.send(err.message);
    }    
});

async function setOrderFields(rows, client){

    for(const order of rows)  {
        await setDishes(rows, client, order);
        await setUser(rows, client, order);
    }
}

async function setDishes(rows, client,order){

    order.Dishes = (await client.query(`select * from getDishesByOrderId(${order.Id})`)).rows;
    
    await setCategory(client, order.Dishes);

    return rows;
}

async function setCategory(client,dishes){
    for(const dish of dishes){
        
        dish.Category = (await client.query(`select * from getById(null::"Category",${dish.CategoryId})`)).rows[0];

        delete dish.CategoryId;
    }
}

async function setUser(rows, client, order){

    order.Customer = (await client.query(`select * from getById(null::"User", ${order.CustomerId})`)).rows[0];

    order.CurrentEmployee = !!order.EmployeeId ? (await client.query(`select * from getById(null::"User", ${order.EmployeeId})`)).rows[0] : null;

    delete order.EmployeeId;

    delete order.CustomerId;

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