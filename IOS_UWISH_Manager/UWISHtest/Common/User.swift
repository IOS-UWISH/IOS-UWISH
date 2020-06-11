//
//  User.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import Foundation

class User: Codable{
    internal init(uuid: Int? = nil, name: String? = nil, status: Bool? = nil) {
        self.uuid = uuid
        self.name = name
        self.status = status
    }
    
    var uuid : Int!
    var name : String!
    var status : Bool!
    
    public func info() -> String {
        return "uuid: \(uuid!),name: \(name!),status: \(status!)"
    }
    
    public static func info(_ users:[User]) -> String {
        var text = ""
        for user in users {
            text += user.info() + "\n"
        }
        return text
    }
    
    
    
}
