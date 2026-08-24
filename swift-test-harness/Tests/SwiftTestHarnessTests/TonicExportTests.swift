import Testing
import Tonic

@Suite("Tonic Swift Export Suite")
struct TonicExportTests {
    @Test("Swift module loads cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true), "Tonic swift module imported cleanly")
    }

    @Test("gRPC Code and Status creation")
    func grpcStatus() {
        let status = Status.Companion.shared.ok(message: "all good")
        #expect(status.code() == Code.Ok)
        #expect(status.message() == "all good")
    }
}
