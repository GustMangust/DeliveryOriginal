using DeliveryOriginal.Admin.Core.Helpers;
using System.ComponentModel.DataAnnotations;

namespace DeliveryOriginal.Admin.Models
{
    public class LoginViewModel
    {
        private string _password;
        [Required]
        [Display(Name = "Login")]
        public string Login { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password
        {
            get { return _password; }
            set
            {
                _password = CryptographyHelper.SHA512(value);
            }
        }
    }
}