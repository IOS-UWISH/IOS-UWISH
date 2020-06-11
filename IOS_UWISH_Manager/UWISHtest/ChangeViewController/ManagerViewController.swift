//
//  ManagerViewController.swift
//  UWISHtest
//
//  Created by 田侑生 on 2020/6/11.
//  Copyright © 2020 Nancy. All rights reserved.
//

import UIKit

class ManagerViewController: UIViewController {

    @IBOutlet weak var ManagerName: UILabel!
    
    @IBOutlet weak var tfOldPassword: UITextField!
    
    @IBOutlet weak var tfNewPassword: UITextField!
    
    @IBOutlet weak var tfCorrectPassword: UITextField!
    
    var managerName = "" //初始值
    var oldpassword = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        let userDefaults = UserDefaults.standard
        managerName = userDefaults.value(forKey: "ManagerName") as! String
        oldpassword = userDefaults.value(forKey: "ManagerPassword") as! String
        ManagerName.text = managerName
        tfOldPassword.text = oldpassword
        // Do any additional setup after loading the view.
    }
    
    
    
    @IBAction func Update(_ sender: Any) {
        
        let newPassword = tfNewPassword.text ?? ""
        let userDefaults = UserDefaults.standard
        userDefaults.set(newPassword, forKey: "ManagerPassword")
        let manager = Manager(Uuid: managerName, Password: newPassword)
        
        
        
        if tfNewPassword.text == tfCorrectPassword.text,tfNewPassword.text != nil,tfCorrectPassword.text != nil{
            let userDefaults = UserDefaults.standard
            userDefaults.set(tfNewPassword.text, forKey: "ManagerPassword")
            tfOldPassword.text = userDefaults.value(forKey: "ManagerPassword") as? String
        NetworkController.shared.managerupdate(manager: manager) { (count) in
            print(count ?? "") }}
        else{print(Error.self)}//預設值
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


