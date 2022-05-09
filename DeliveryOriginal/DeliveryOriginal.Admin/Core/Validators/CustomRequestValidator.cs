using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI.WebControls;
using System.Web.Util;

namespace DeliveryOriginal.Admin.Core.Validators
{
    public class CustomRequestValidator : RequestValidator
    {
        public CustomRequestValidator() { }


        protected override bool IsValidRequestString(HttpContext context, 
                                                     string value, 
                                                     RequestValidationSource requestValidationSource, 
                                                     string collectionKey, 
                                                     out int validationFailureIndex)
        {
            if (!IsSafeHtml(value, out validationFailureIndex))
            {
                throw new DeliveryOriginalMvcException($"A potentially dangerous input value was detected: {value}");
            }

            return base.IsValidRequestString(context, value, requestValidationSource, collectionKey, out validationFailureIndex);
        }

        bool IsSafeHtml(string s, out int outIndex)
        {
            outIndex = s.ToLower().IndexOf("javascript:");
            return outIndex == -1;
        }
    }

    public class DeliveryOriginalMvcException : Exception
    {
        public DeliveryOriginalMvcException()
        { }

        public DeliveryOriginalMvcException(string message) : base(message)
        { }
    }
}