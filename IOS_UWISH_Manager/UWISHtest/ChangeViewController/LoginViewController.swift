//
//  LoginViewController.swift
//  UWISHtest
//
//  Created by 田侑生 on 2020/6/11.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var tfAccount: UITextField!
    
    @IBOutlet weak var tfPassword: UITextField!
    
    @IBOutlet weak var btLogin: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        btLogin.layer.cornerRadius = 8.0
        btLogin.layer.masksToBounds = true
    
    
    }
    
    
    @IBAction func login(_ sender: Any) {
        
        guard
                   let account = tfAccount.text,
               let password = tfPassword.text
                   
                   else{return}
        
        NetworkController.shared.login(account: account, password: password) { (count) in
                    if count != 0 {
                        
                        
                        let userDefaults = UserDefaults.standard
                        userDefaults.set(account, forKey: "ManagerName")
                        userDefaults.set(password, forKey: "ManagerPassword")
        //                tfAccount.text = ""
        //                tfPassword.text = ""
                        DispatchQueue.main.async {
                            self.performSegue(withIdentifier: "lovePeter", sender: nil)
                        
                        }
                    } else {
                        print("error")
                    }
                }
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
