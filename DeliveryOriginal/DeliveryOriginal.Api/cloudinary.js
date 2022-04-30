const cloudinary = require('cloudinary').v2;

cloudinary.config({ 
    cloud_name: 'gustmangust', 
    api_key: '718477222848953', 
    api_secret: '7PksEMQd1XJkoIc1OwdQ-Wwq9ak',
    secure: true
  });

const imageUrl = 'https://res.cloudinary.com/gustmangust/image/upload/v1649983179/'

function uploads(fileBase64,publicId){
        cloudinary.uploader.upload(
            'data:image/jpeg;base64,' + fileBase64, {public_id: publicId, overwrite: true},
            function(error, result) {console.log(result, error)});
};

function destroyImage(imageUrl){
    let publicId = imageUrl.substring(imageUrl.lastIndexOf('/')+1);
    
    cloudinary.uploader.destroy(publicId, function(error,result) { console.log(result,error) });
}
        
module.exports = {
    imageUrl,
    uploads,
    destroyImage
};