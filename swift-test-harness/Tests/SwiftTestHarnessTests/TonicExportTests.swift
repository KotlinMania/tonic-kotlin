#if canImport(Testing)
import Testing
import Tonic

@Suite("Tonic Swift Export Smoke Test")
struct TonicExportTests {
    @Test("Swift module loads")
    func swiftModuleLoads() throws {
        #expect(true)
    }
}
#elseif canImport(XCTest)
import XCTest
import Tonic

final class TonicExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "Tonic swift module imported cleanly")
    }
}
#endif
