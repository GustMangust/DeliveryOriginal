using System.ComponentModel.DataAnnotations;

namespace DeliveryOriginal.Admin.Models
{
    public class LoginViewModel
    {
        [Required]
        [Display(Name = "Login")]
        [EmailAddress]
        public string Login { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }
    }
}