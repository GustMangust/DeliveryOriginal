using System;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Reflection;

namespace DeliveryOriginal.Admin.Core.Extensions
{
    public static class EnumExtensions
    {
        public static string GetDisplayName(this Enum enumValue)
        {
            try
            {
                return enumValue?.GetType()
                    ?.GetMember(enumValue.ToString())
                    ?.FirstOrDefault()
                    ?.GetCustomAttribute<DisplayAttribute>()
                    ?.GetName()
                    ?? enumValue.ToString();
            }
            catch
            {
                return enumValue.ToString();
            }
        }

    }
}