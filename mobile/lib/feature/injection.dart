import 'package:get_it/get_it.dart';

import 'util/controllers/image_picker_controller.dart';

class Injection {
  static final GetIt getIt = GetIt.instance;

  Future<void> init() async {
    getIt.registerSingleton<ImagePickerController>(ImagePickerController());
  }
}
