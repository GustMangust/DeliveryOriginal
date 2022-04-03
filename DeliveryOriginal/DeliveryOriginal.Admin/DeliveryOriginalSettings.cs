using System.Configuration;

namespace DeliveryOriginal.Admin
{
    public class DeliveryOriginalSettings
    {
        public static string ApiUrl => ConfigurationManager.AppSettings["ApiUrl"];
    }
}