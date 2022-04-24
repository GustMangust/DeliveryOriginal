using DeliveryOriginal.Admin.Core.Helpers;
using System.ComponentModel.DataAnnotations;

namespace DeliveryOriginal.Admin.Models
{
    public class RegisterViewModel
    {
        private string _password;
        private string _confirmPassword;
        [Required]
        [Display(Name = "Login")]
        public string Login { get; set; }
        [Required]
        [Display(Name = "Full Name")]
        public string FullName { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 8)]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { 
            get { return _password; }
            set
            {
                _password = CryptographyHelper.SHA512(value);
            }
        }

        [DataType(DataType.Password)]
        [Display(Name = "Confirm password")]
        public string ConfirmPassword {
            get { return _confirmPassword; }
            set
            {
                _confirmPassword = CryptographyHelper.SHA512(value);
            }
        }
    }
}