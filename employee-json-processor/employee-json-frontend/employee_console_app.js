// Employee Management Frontend - Run this in IntelliJ
const API_BASE_URL = 'http://localhost:8080/api/employees';

console.log('🚀 Employee Management System');
console.log('📡 Backend URL:', API_BASE_URL);
console.log('');

// Test function - check if backend is working
async function testBackend() {
    try {
        console.log('Testing connection to backend...');
        const response = await fetch(API_BASE_URL);
        const employees = await response.json();
        console.log('✅ Backend is working!');
        console.log('📋 Employees found:', employees.length);

        // Show employees in a nice table
        console.log('\n📊 EMPLOYEE LIST:');
        console.log('=================================================');
        employees.forEach(emp => {
            console.log(`ID: ${emp.id} | Name: ${emp.name} | Dept: ${emp.department} | Salary: $${emp.salary}`);
        });
        console.log('=================================================');

    } catch (error) {
        console.log('❌ Cannot connect to backend. Make sure:');
        console.log('   1. Spring Boot is running (mvn spring-boot:run)');
        console.log('   2. Backend URL is correct:', API_BASE_URL);
        console.log('   3. CORS is enabled in EmployeeController');
    }
}

// Load employees from JSON file
async function loadFromJSON() {
    try {
        console.log('📥 Loading employees from JSON file...');
        const response = await fetch(`${API_BASE_URL}/load-from-json`, {
            method: 'POST'
        });
        const employees = await response.json();
        console.log('✅ Success! Loaded', employees.length, 'employees');
        return employees;
    } catch (error) {
        console.log('❌ Error loading from JSON:', error.message);
    }
}

// Add a new employee
async function addEmployee() {
    const employeeData = {
        name: "Test Employee from IntelliJ",
        email: "test@intellij.com",
        position: "Developer",
        department: "IT",
        salary: 50000
    };

    try {
        console.log('➕ Adding new employee...');
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(employeeData)
        });
        const savedEmployee = await response.json();
        console.log('✅ Employee added successfully!');
        console.log('   ID:', savedEmployee.id, 'Name:', savedEmployee.name);
        return savedEmployee;
    } catch (error) {
        console.log('❌ Error adding employee:', error.message);
    }
}

// Get employee count
async function getCount() {
    try {
        const response = await fetch(`${API_BASE_URL}/count`);
        const count = await response.json();
        console.log('📊 Total employees in database:', count);
        return count;
    } catch (error) {
        console.log('❌ Error getting count:', error.message);
    }
}

// Main function - run all tests
async function main() {
    console.log('🏁 STARTING EMPLOYEE MANAGEMENT TESTS...\n');

    // Test 1: Check backend connection
    await testBackend();

    // Test 2: Get employee count
    await getCount();

    // Test 3: Load from JSON (optional)
    // await loadFromJSON();

    // Test 4: Add new employee (optional)
    // await addEmployee();

    console.log('\n🎯 TESTS COMPLETED!');
    console.log('💡 You can call these functions individually:');
    console.log('   - testBackend()');
    console.log('   - loadFromJSON()');
    console.log('   - addEmployee()');
    console.log('   - getCount()');
}

// Run the main function
main();