using DeliveryOriginal.Admin.Core.Helpers;
using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize(Role = RoleGroup.Regulars)]
    public class CategoryController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public CategoryController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            var categories = await UnitOfWork.CategoryRepository.GetAll();

            return View(categories);
        }

        [HttpGet]
        public ActionResult CreateCategory()
        {
            var category = new Category();

            return View(category);
        }

        [HttpPost]
        public async Task<ActionResult> CreateCategory(Category model)
        {
            var file = GetRequestFirstFile(Request);
            if (file != null)
            {
                var fileExtension = Path.GetExtension(Request?.Files[0]?.FileName)?.ToLower();
                if (!ImageResizer.AcceptedImageFormats.Contains(fileExtension))
                {
                    ModelState.AddModelError("Location Logo", @"Error loading Location Logo. Please check if image is in BMP, GIF, EXIF, JPG, JPEG, PNG or TIFF format.");
                }
            }

            if (ModelState.IsValid)
            {
                if (file != null)
                {
                    try
                    {
                        var imageUrl = await ImageResizer.ScaleImageCrop(file.InputStream, 
                                                                        Int32.Parse(DeliveryOriginalSettings.CategoryImageWidth), 
                                                                        Int32.Parse(DeliveryOriginalSettings.CategoryImageHeight));
                        model.ImageUrl = imageUrl;
                    }
                    catch (Exception ex)
                    {
                        return View("Error", ex);
                    }
                }
            } 
            else
            {
                return View();
            }

            await UnitOfWork.CategoryRepository.Insert(model);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task<ActionResult> EditCategory(int id)
        {
            var category = await UnitOfWork.CategoryRepository.Get(id);

            return View(category);
        }

        [HttpPost]
        public async Task<ActionResult> EditCategory(Category model)
        {
            var file = GetRequestFirstFile(Request);
            if (file != null)
            {
                var fileExtension = Path.GetExtension(Request?.Files[0]?.FileName)?.ToLower();
                if (!ImageResizer.AcceptedImageFormats.Contains(fileExtension))
                {
                    ModelState.AddModelError("Location Logo", @"Error loading Location Logo. Please check if image is in BMP, GIF, EXIF, JPG, JPEG, PNG or TIFF format.");
                }
            }

            if (ModelState.IsValid)
            {
                if (file != null)
                {
                    try
                    {
                        var imageUrl = await ImageResizer.ScaleImageCrop(file.InputStream,
                                                                        Int32.Parse(DeliveryOriginalSettings.CategoryImageWidth),
                                                                        Int32.Parse(DeliveryOriginalSettings.CategoryImageHeight));
                        model.ImageUrl = imageUrl;
                    }
                    catch (Exception ex)
                    {
                        return View("Error", ex);
                    }
                }
            }
            else
            {
                return View();
            }

            await UnitOfWork.CategoryRepository.Update(model);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task DeleteCategory(int categoryId)
        {
            await UnitOfWork.CategoryRepository.Delete(categoryId);
        }

        [HttpPost]
        public async Task<ActionResult> DeleteCategoryLogo(int categoryId)
        {
            var category = await UnitOfWork.CategoryRepository.Get(categoryId);

            await MediaStorageHelper.DeleteFromCloudStorageAsync(category.ImageUrl);
            category.ImageUrl = "";

            await UnitOfWork.CategoryRepository.Update(category);

            return new HttpStatusCodeResult(200);
        }

        public HttpPostedFileBase GetRequestFirstFile(HttpRequestBase request)
        {
            return request.Files.Count > 0 && request.Files[0] != null && request.Files[0].ContentLength > 0
                ? request.Files[0]
                : null;
        }
    }
}