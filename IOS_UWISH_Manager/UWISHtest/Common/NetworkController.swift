//
//  NetworkController.swift
//  UWTest
//
//  Created by 李佳芸 on 2020/6/7.
//  Copyright © 2020 Cathie. All rights reserved.
//

import UIKit

struct GetMissionPostedPostData: Encodable {
    let action = "getMissionPosted"
    let userId: Int
}

struct LoginPostData: Encodable {
    let action = "ManagerLogin"
    let account: String
    let password: String
}

struct ManagerinfoPostData: Encodable {
    let action = "findById"
    let uuid: String
    
}

struct ManagerupdatePostData: Encodable {
    let action = "managerUpdate"
   let manager: Manager
    
    enum CodingKeys: String, CodingKey {
        case action
        case manager
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(action, forKey: .action)
        
        let jsonEncoder = JSONEncoder()
        if let managerData = try? jsonEncoder.encode(manager), let managerString = String(data: managerData, encoding: .utf8) {
            try container.encode(managerString, forKey: .manager)
        }
        
       
        
    }
}

struct CreateMissionPostData: Encodable {
    let action = "insert"
    let mission: Mission
    let image1: String?
    let image2: String?
    
    enum CodingKeys: String, CodingKey {
        case action
        case mission
        case image1
        case image2
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(action, forKey: .action)
        
        let jsonEncoder = JSONEncoder()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        jsonEncoder.dateEncodingStrategy = .formatted(dateFormatter)
        if let missionData = try? jsonEncoder.encode(mission), let missionString = String(data: missionData, encoding: .utf8) {
            try container.encode(missionString, forKey: .mission)
        }
        
        try container.encodeIfPresent(image1, forKey: .image1)
        try container.encodeIfPresent(image2, forKey: .image2)
        
    }
    
}

class NetworkController {
    
    static let shared = NetworkController()
    
    let baseURL = URL(string: "http://localhost:8080/UWserver/")!
    
    func login(account: String,password: String,completion: @escaping (Int?) -> Void ){
        let url = baseURL.appendingPathComponent("ManagerServlet")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        let data = LoginPostData(account: account, password: password)
        let jsonEncoder = JSONEncoder()
        let jsonData = try? jsonEncoder.encode(data)
        request.httpBody = jsonData
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            let jsonDecoder = JSONDecoder()
            if let data = data, let count = try? jsonDecoder.decode(Int.self, from: data) {
                // dump(missions)
                completion(count)
            } else {
                completion(nil)
            }
        }.resume()
    }
    
    func managerinfo(uuid: String,completion: @escaping (Manager?) -> Void ){
        let url = baseURL.appendingPathComponent("ManagerServlet")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        let data = ManagerinfoPostData(uuid: uuid)
        let jsonEncoder = JSONEncoder()
        let jsonData = try? jsonEncoder.encode(data)
        request.httpBody = jsonData
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            let jsonDecoder = JSONDecoder()
            if let data = data, let managers = try? jsonDecoder.decode([Manager].self, from: data) {
             dump(managers)
//            completion(managers)
            } else {
                completion(nil)
            }
        }.resume()
    }
    
    func managerupdate(manager:Manager,completion: @escaping (Int?) -> Void ){
            let url = baseURL.appendingPathComponent("ManagerServlet")
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            let data = ManagerupdatePostData(manager:manager)
            let jsonEncoder = JSONEncoder()
            let jsonData = try? jsonEncoder.encode(data)
            request.httpBody = jsonData
            URLSession.shared.dataTask(with: request) { (data, response, error) in
                let jsonDecoder = JSONDecoder()
                if let data = data, let number = try? jsonDecoder.decode(Int.self, from: data) {
                        completion(number)
                    } else {
                        completion(nil)
                    }
                }.resume()
        }
    
    func  fetchMissionsFromAsset(userId: Int, completion: @escaping ([Mission]?) -> Void ) {
        
        let jsonDecoder = JSONDecoder()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        jsonDecoder.dateDecodingStrategy = .formatted(dateFormatter)
        if let data = NSDataAsset(name: "missions")?.data, let missions = try? jsonDecoder.decode([Mission].self, from: data) {
            // dump(missions)
            completion(missions)
        } else {
            completion(nil)
        }
        
    }
    
    func fetchMissions(userId: Int, completion: @escaping ([Mission]?) -> Void ) {
        
        
        let url = baseURL.appendingPathComponent("MissionServlet")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        let data = GetMissionPostedPostData(userId: userId)
        let jsonEncoder = JSONEncoder()
        let jsonData = try? jsonEncoder.encode(data)
        request.httpBody = jsonData
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            let jsonDecoder = JSONDecoder()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            jsonDecoder.dateDecodingStrategy = .formatted(dateFormatter)
            if let data = data, let missions = try? jsonDecoder.decode([Mission].self, from: data) {
                // dump(missions)
                completion(missions)
            } else {
                completion(nil)
            }
        }.resume()
    }
    
    func createMission(mission: Mission, image1: UIImage?, image2: UIImage?, completion: @escaping (Int?) -> Void ) {
        let url = baseURL.appendingPathComponent("MissionServlet")
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        var image1Base64String: String?
        var image2Base64String: String?
        
        if let image = image1,
            let data = image.jpegData(compressionQuality: 0.9) {
            image1Base64String = data.base64EncodedString()
        }
        if let image = image2,
            let data = image.jpegData(compressionQuality: 0.9) {
            image2Base64String = data.base64EncodedString()
        }
        
        
        let data = CreateMissionPostData(mission: mission, image1: image1Base64String, image2: image2Base64String)
        
        let jsonEncoder = JSONEncoder()
        let jsonData = try? jsonEncoder.encode(data)
        request.httpBody = jsonData
        
        URLSession.shared.dataTask(with: request) { (data, response, error) in
            let jsonDecoder = JSONDecoder()
            
            if let data = data, let number = try? jsonDecoder.decode(Int.self, from: data) {
                completion(number)
            } else {
                completion(nil)
            }
        }.resume()
    }
    
    
}
