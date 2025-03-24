const express = require('express');
const fs = require('fs');
const path = require('path');
const multer = require('multer');

const app = express();

app.use(express.json());

// 设置图片上传存储位置和文件名，根据字段名动态设置文件夹
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    if (file.fieldname === 'avatar') {
      cb(null, 'uploads/avatars/'); // 将头像图片保存到 'uploads/avatars/' 目录
    } else if (file.fieldname === 'club_image') {
      cb(null, 'uploads/club_images/'); // 将俱乐部图片保存到 'uploads/club_images/' 目录
    } else {
      cb(null, 'uploads/'); // 默认上传到 'uploads/' 目录
    }
  },
  filename: function (req, file, cb) {
    cb(null, Date.now() + path.extname(file.originalname)); // 以时间戳命名文件
  }
});

const upload = multer({ storage: storage });

// 读取数据
const dataPath = path.join(__dirname, 'json', 'data.json');
let data = JSON.parse(fs.readFileSync(dataPath));

// GET API - 获取所有老师、学生和公司信息
app.get('/data', (req, res) => {
  res.status(200).json({
    status: 'success',
    data
  });
});

// POST API - 添加老师、学生或公司信息
// POST API - 添加老师、学生或公司信息，并上传图片
app.post('/add', upload.fields([{ name: 'avatar', maxCount: 1 }, { name: 'club_image', maxCount: 1 }]), (req, res) => {
  const newData = req.body;

  // 如果用户上传了图片，保存图片路径
  const avatarPath = req.files['avatar'] ? `/uploads/avatars/${req.files['avatar'][0].filename}` : null;
  const clubImagePath = req.files['club_image'] ? `/uploads/club_images/${req.files['club_image'][0].filename}` : null;

  if (newData.type === 'teacher') {
    // 添加新的老师
    data.teachers.push({
      avatar: avatarPath || newData.avatar, // 保存头像图片路径或者使用默认路径
      username: newData.username,
      password: newData.password,
      school: newData.school,
      position: newData.position,
      phone: newData.phone,
      club: newData.club,
      club_image: clubImagePath || newData.club_image, // 保存俱乐部图片路径或者使用默认路径
      email: newData.email
    });
  } else if (newData.type === 'student') {
    // 添加新的学生
    data.students.push({
      avatar: avatarPath || newData.avatar,
      username: newData.username,
      password: newData.password,
      school: newData.school,
      studentid: newData.studentid,
      phone: newData.phone,
      club: newData.club,
      club_image: clubImagePath || newData.club_image,
      email: newData.email,
      great: newData.great
    });
  } else if (newData.type === 'company') {
    // 添加新的公司
    data.companys.push({
      avatar: avatarPath || newData.avatar,
      username: newData.username,
      password: newData.password,
      company: newData.company,
      companyid: newData.companyid,
      phone: newData.phone,
      email: newData.email,
      great: newData.great
    });
  } else {
    return res.status(400).json({
      status: 'fail',
      message: 'Invalid type, must be either "teacher", "student" or "company"'
    });
  }

  // 更新文件数据
  fs.writeFile(dataPath, JSON.stringify(data, null, 2), (err) => {
    if (err) {
      return res.status(500).json({
        status: 'error',
        message: 'Unable to save data'
      });
    }

    res.status(201).json({
      status: 'success',
      data
    });
  });
});

// DELETE API - 根据类型和用户名删除老师、学生或公司信息
app.delete('/delete/:type/:username', (req, res) => {
  const { type, username } = req.params;

  if (type === 'teacher') {
    // 根据用户名查找老师
    const teacherIndex = data.teachers.findIndex(teacher => teacher.username === username);

    if (teacherIndex === -1) {
      return res.status(404).json({
        status: 'fail',
        message: `Teacher with username ${username} not found`
      });
    }

    // 删除老师信息
    data.teachers.splice(teacherIndex, 1);

  } else if (type === 'student') {
    // 根据用户名查找学生
    const studentIndex = data.students.findIndex(student => student.username === username);

    if (studentIndex === -1) {
      return res.status(404).json({
        status: 'fail',
        message: `Student with username ${username} not found`
      });
    }

    // 删除学生信息
    data.students.splice(studentIndex, 1);

  } else if (type === 'company') {
    // 根据用户名查找公司
    const companyIndex = data.companys.findIndex(company => company.username === username);

    if (companyIndex === -1) {
      return res.status(404).json({
        status: 'fail',
        message: `Company with username ${username} not found`
      });
    }

    // 删除公司信息
    data.companys.splice(companyIndex, 1);

  } else {
    return res.status(400).json({
      status: 'fail',
      message: 'Invalid type, must be either "teacher", "student" or "company"'
    });
  }

  // 更新文件数据
  fs.writeFile(dataPath, JSON.stringify(data, null, 2), (err) => {
    if (err) {
      return res.status(500).json({
        status: 'error',
        message: 'Unable to save data'
      });
    }

    res.status(200).json({
      status: 'success',
      message: `${type.charAt(0).toUpperCase() + type.slice(1)} with username ${username} deleted`,
      data
    });
  });
});

// 使用 multer 处理头像和俱乐部图片的上传
app.put('/update/:type/:username', upload.fields([{ name: 'avatar', maxCount: 1 }, { name: 'club_image', maxCount: 1 }]), (req, res) => {
  const { type, username } = req.params;
  const updatedData = req.body;

  // 如果上传了新的图片，保存图片路径
  const avatarPath = req.files['avatar'] ? `/uploads/avatars/${req.files['avatar'][0].filename}` : null;
  const clubImagePath = req.files['club_image'] ? `/uploads/club_images/${req.files['club_image'][0].filename}` : null;

  if (type === 'teacher') {
    // 查找要修改的老师
    const teacher = data.teachers.find(teacher => teacher.username === username);

    if (!teacher) {
      return res.status(404).json({
        status: 'fail',
        message: `Teacher with username ${username} not found`
      });
    }

    // 更新老师信息
    teacher.avatar = avatarPath || updatedData.avatar || teacher.avatar;
    teacher.username = updatedData.username || teacher.username;
    teacher.password = updatedData.password || teacher.password;
    teacher.school = updatedData.school || teacher.school;
    teacher.position = updatedData.position || teacher.position;
    teacher.phone = updatedData.phone || teacher.phone;
    teacher.club = updatedData.club || teacher.club;
    teacher.club_image = clubImagePath || updatedData.club_image || teacher.club_image;
    teacher.email = updatedData.email || teacher.email;

  } else if (type === 'student') {
    // 查找要修改的学生
    const student = data.students.find(student => student.username === username);

    if (!student) {
      return res.status(404).json({
        status: 'fail',
        message: `Student with username ${username} not found`
      });
    }

    // 更新学生信息
    student.avatar = avatarPath || updatedData.avatar || student.avatar;
    student.username = updatedData.username || student.username;
    student.password = updatedData.password || student.password;
    student.school = updatedData.school || student.school;
    student.studentid = updatedData.studentid || student.studentid;
    student.phone = updatedData.phone || student.phone;
    student.club = updatedData.club || student.club;
    student.club_image = clubImagePath || updatedData.club_image || student.club_image;
    student.email = updatedData.email || student.email;
    student.great = updatedData.great || student.great;

  } else if (type === 'company') {
    // 查找要修改的公司
    const company = data.companys.find(company => company.username === username);

    if (!company) {
      return res.status(404).json({
        status: 'fail',
        message: `Company with username ${username} not found`
      });
    }

    // 更新公司信息
    company.avatar = avatarPath || updatedData.avatar || company.avatar;
    company.username = updatedData.username || company.username;
    company.password = updatedData.password || company.password;
    company.company = updatedData.company || company.company;
    company.companyid = updatedData.companyid || company.companyid;
    company.phone = updatedData.phone || company.phone;
    company.email = updatedData.email || company.email;
    company.great = updatedData.great || company.great;

  } else {
    return res.status(400).json({
      status: 'fail',
      message: 'Invalid type, must be either "teacher", "student" or "company"'
    });
  }

  // 更新文件数据
  fs.writeFile(dataPath, JSON.stringify(data, null, 2), (err) => {
    if (err) {
      return res.status(500).json({
        status: 'error',
        message: 'Unable to save data'
      });
    }

    res.status(200).json({
      status: 'success',
      data
    });
  });
});

// 启动服务器
const port = 8000;
app.listen(port, () => {
  console.log(`App running on port ${port}...`);
});
