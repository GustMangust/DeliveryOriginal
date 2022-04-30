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

module.exports = {
    getClient
}