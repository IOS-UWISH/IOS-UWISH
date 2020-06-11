//
//  Manager.swift
//  UWISH
//
//  Created by 田侑生 on 2020/6/8.
//  Copyright © 2020 田侑生. All rights reserved.
//

import Foundation

struct Manager: Codable {
    var Uuid: String
    var Password: String
    
    
//    public init(_ uuid: Int, _ password: Int) {
//        self.uuid = uuid
//        self.password = password
//
//    }
//    
//    public func info() -> String {
//        return "uuid: \(uuid!), password: \(password!)"
//    }
//
//    public static func info(_ managers: [Manager]) -> String {
//        var text = ""
//        for manager in managers {
//            text += manager.info() + "\n"
//        }
//        return text
//    }
//
}

//public class Manager implements Serializable {
//    private String Uuid;
//    private String Password;
//
//    public Manager(String uuid, String password) {
//        this.Uuid = uuid;
//        this.Password = password;
//    }
//
//    public Manager(String password){
//        this.Password = password;
//    }
//
//    public void setFields(String password) {
//        this.Password = password;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return this.Uuid == ((Manager) obj).Uuid;
//    }
//
//    public String getUuid() {
//        return Uuid;
//    }
//
//    public void setUuid(String uuid) {
//        Uuid = uuid;
//    }
//
//    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String password) {
//        Password = password;
//    }
//}
