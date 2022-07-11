import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Method Channel Practice',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Method Channel Practice'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('com.example.methodchannel_practice/methodChannel');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            osVersionButton()
          ],
        ),
      ),
    );
  }


  Builder osVersionButton() {
    return Builder(
        builder: (context) => ElevatedButton(
            onPressed: () => {
              _getOSVersion().then((value) {
                Scaffold.of(context).showSnackBar(
                  SnackBar(
                    content: Text(value!),
                    duration: const Duration(seconds: 3),
                  )
                );
              })
            },
            child: const Text('OS Version')
        )
    );
  }

  // Methode Chanel Functions
  Future<String?> _getOSVersion() async {
    String? version;
    try{
      version = await platform.invokeMethod('getOSVersion');
    } on PlatformException catch (e){
      version = e.message;
    }
    return version;
  }
}
