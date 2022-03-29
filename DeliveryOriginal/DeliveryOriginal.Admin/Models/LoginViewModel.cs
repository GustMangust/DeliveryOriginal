using System.ComponentModel.DataAnnotations;

namespace DeliveryOriginal.Admin.Models
{
    public class LoginViewModel
    {
        [Required]
        [Display(Name = "UserName")]
        [EmailAddress]
        public string UserName { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }
    }
}