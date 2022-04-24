using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Core.Helpers
{
    public class ImageResizer
    {
        public static string[] AcceptedImageFormats = { ".bmp", ".gif", ".tiff", ".png", ".jpg", ".jpeg", ".exif", };
        public const string SvgExtension = ".svg";

        public delegate void ImageTransformationHandler(Stream stream, int width, int height, string path);
        public static async Task ScaleImageDisproportional(Stream stream, int width, int height)
        {
            Bitmap imgBitmap = new Bitmap(stream);
            await TransformAndSave(imgBitmap, width, height, imgBitmap.RawFormat);
        }
        public static async Task<string> ScaleImageCrop(Stream stream, int width, int height)
        {
            Bitmap original = new Bitmap(stream);
            Bitmap imgBitmap = CroupImage(original, width, height);
            var url = await TransformAndSave(imgBitmap, width, height, original.RawFormat);
            return url;
        }
        private static Bitmap CroupImage(Bitmap originalBitmap, int width, int height)
        {
            int cutWidth = originalBitmap.Width;
            int cutHeight = originalBitmap.Height;

            float proportions = (float)width / height;
            float originalBitmapProportions = (float)originalBitmap.Width / originalBitmap.Height;

            if (originalBitmapProportions > proportions)
            {
                //cut by width
                cutWidth = (int)(originalBitmap.Width * proportions / originalBitmapProportions);
            }
            else
            {
                //cut by height
                cutHeight = (int)(originalBitmap.Height / proportions * originalBitmapProportions);
            }
            if ((cutWidth == width) && (cutHeight == height))
                return originalBitmap;
            Bitmap cutted = originalBitmap.Clone(new Rectangle(0, 0, cutWidth, cutHeight), originalBitmap.PixelFormat);
            return cutted;
        }

        private static async Task<string> TransformAndSave(Bitmap fromBitmap, int width, int height, ImageFormat imgFromat)
        {
            using (var memoryStream = new MemoryStream())
            {
                Bitmap result = new Bitmap(fromBitmap, width, height);
                result.Save(memoryStream, ImageFormat.Jpeg);
                var byteArray = memoryStream.GetBuffer();
                var imageString = Convert.ToBase64String(byteArray);
                var url = await MediaStorageHelper.UploadToCloudStorageAsync(imageString);
                return url;
            }
        }
    }
}