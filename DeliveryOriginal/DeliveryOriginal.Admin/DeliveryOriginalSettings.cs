using System.Configuration;

namespace DeliveryOriginal.Admin
{
    public class DeliveryOriginalSettings
    {
        public static string ApiUrl => ConfigurationManager.AppSettings["ApiUrl"];
        public static string DishImageWidth => ConfigurationManager.AppSettings["DishImageWidth"];
        public static string DishImageHeight => ConfigurationManager.AppSettings["DishImageHeight"];
        public static string CategoryImageWidth => ConfigurationManager.AppSettings["CategoryImageWidth"];
        public static string CategoryImageHeight => ConfigurationManager.AppSettings["CategoryImageHeight"];
    }
}